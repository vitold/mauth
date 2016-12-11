package com.vitold.mauth

import akka.http.scaladsl.model.headers.{BasicHttpCredentials, HttpChallenges, HttpCredentials}
import akka.http.scaladsl.server.directives.{AuthenticationDirective, AuthenticationResult}
import akka.http.scaladsl.server.directives.Credentials.{Missing, Provided}
import akka.http.scaladsl.server.Directives._
import com.vitold.mauth.model.UserSession

import scala.concurrent.{ExecutionContext, Future}

trait Authentication {

  val authService: AuthService

  implicit val ec: ExecutionContext

  val realm: String

  private val oAuth2Authenticator: AsyncAuthenticator[UserSession] = {
    case Provided(token) =>
      authService.getSession(token)
    case Missing =>
      Future.successful(None)
  }

  type CustomAuthenticator[T] = Option[HttpCredentials] => Future[Option[T]]

  def customAuthenticateAsync[T](authenticator: CustomAuthenticator[T]): AuthenticationDirective[T] = {
    authenticateOrRejectWithChallenge[BasicHttpCredentials, T] { cred ⇒
      authenticator(cred).map {
        case Some(t) => AuthenticationResult.success(t)
        case None => AuthenticationResult.failWithChallenge(HttpChallenges.oAuth2(realm))
      }
    }
  }

  val basicAuth = customAuthenticateAsync(authService.checkCredentials)

  val oAuth = authenticateOAuth2Async(realm, oAuth2Authenticator)

}
