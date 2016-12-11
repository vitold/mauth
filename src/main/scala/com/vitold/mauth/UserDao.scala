package com.vitold.mauth

import com.vitold.mauth.model.User

import scala.concurrent.Future

trait UserDao {

  def getUser(login: String): Future[Option[User]]

}
