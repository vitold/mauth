package com.vitold.mauth.module

import com.vitold.mauth.{AuthService, BearerTokenGenerator, SessionService}
import scaldi.Module
import scredis.Redis

class SessionModule extends Module {

  bind[BearerTokenGenerator] to new BearerTokenGenerator
  bind[AuthService] to injected[AuthService]
  bind[SessionService] to injected[SessionService]
  bind[Redis] to Redis()

}
