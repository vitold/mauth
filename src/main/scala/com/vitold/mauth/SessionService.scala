package com.vitold.mauth

import com.vitold.mauth.model.{User, UserSession}
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._
import scredis.Redis

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class SessionService(
                      redis: Redis,
                      bearerTokenGenerator: BearerTokenGenerator,
                      val defaultTtl: FiniteDuration = 10.minutes
                    )(
                      implicit ec: ExecutionContext
                    ) {

  implicit val formats = DefaultFormats


  def createSession(user: User, ttl: FiniteDuration = defaultTtl): Future[Option[UserSession]] = {
    val token = bearerTokenGenerator.generateMD5Token(user.login)
    val userSession = UserSession(user.login, token, user._id)
    redis.set(userSession.sessionToken, write(userSession), Some(ttl)).map {
      result =>
        if (result) {
          Some(userSession)
        } else {
          None
        }
    }
  }

  def getSession(token: String): Future[Option[UserSession]] = {
    touchSession(token).flatMap {
      case true =>
        redis.get(token).map {
          sessionOpt =>
            sessionOpt.map(
              read[UserSession](_)
            )
        }
      case false =>
        Future.successful(None)
    }
  }

  def touchSession(token: String): Future[Boolean] = {
    redis.expire(token, defaultTtl.toSeconds.toInt)
  }

}
