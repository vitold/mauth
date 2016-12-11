package com.vitold.mauth

import akka.http.scaladsl.model.headers.{BasicHttpCredentials, HttpCredentials}
import com.github.t3hnar.bcrypt._
import com.vitold.mauth.model.UserSession

import scala.concurrent.{ExecutionContext, Future}

class AuthService(
                   val sessionService: SessionService,
                   val userDao: UserDao
                 )(
                   implicit val ec: ExecutionContext
                 ) {

  def checkCredentials(credentials: Option[HttpCredentials]): Future[Option[UserSession]] = {
    credentials match {
      case Some(BasicHttpCredentials(login, password)) =>
        userDao.getUser(login).flatMap {
          case Some(user) if password.isBcrypted(user.password) =>
            sessionService.createSession(user)
          case _ =>
            Future.successful(None)
        }
      case _ =>
        Future.successful(None)
    }
  }

  def getSession(token: String) = sessionService.getSession(token)

}
