# jwt-ouath-02 — OAuth2 / OIDC sample (client + resource server + authorization server)

Three standalone Spring Boot apps that together implement the flow:

```
our Spring Boot App  (module: client, http://localhost:8080)
      │
      │ OAuth2 Client — client-id: my-client-id
      │ browser authorization_code + PKCE, then OIDC login
      ▼
┌───────────────────────────────────────────────┐
│ Authorization Server  (module: auth-server)   │
│ http://localhost:9000                          │
│   /oauth2/authorize   login + consent          │
│   /oauth2/token       token issuance (JWT)      │
│   /oauth2/jwks        public signing keys       │
│   /.well-known/openid-configuration            │
└─────────────────────────┬─────────────────────┘
                          │ access token (JWT, RS256)
                          ▼
┌───────────────────────────────────────────────┐
│ Resource Server  (module: resource-server)    │
│ http://localhost:8090                          │
│   GET  /messages   scope: message.read         │
│   POST /messages   scope: message.write        │
└───────────────────────────────────────────────┘
```

Each module is built from the matching Spring Security reference page:

| Module            | Reference |
|-------------------|-----------|
| `auth-server`     | https://docs.spring.io/spring-security/reference/servlet/oauth2/authorization-server/getting-started.html |
| `resource-server` | https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html |
| `client`          | https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html |

## Credentials & registration (all in-memory, sample only)

| What | Value |
|------|-------|
| End user | `user` / `password` |
| Client id / secret | `my-client-id` / `my-client-secret` (`{noop}`, `client_secret_basic`) |
| Grants | `authorization_code`, `refresh_token`, `client_credentials` |
| Redirect URIs | `http://localhost:8080/login/oauth2/code/my-client-id`, `http://127.0.0.1:8080/login/oauth2/code/my-client-id` |
| Scopes | `openid`, `profile`, `message.read`, `message.write` |

> The auth server and client both run on `localhost`, and cookies are not port-scoped,
> so each app sets its own session cookie name (`AUTH_SERVER_SESSION` / `CLIENT_SESSION`)
> to avoid clobbering the other's session mid-flow.

## Run

Build everything:

```bash
./mvnw package
```

Then start the apps **in this order** (the client and resource server discover the
auth server from `issuer-uri` at startup):

```bash
./mvnw -pl auth-server spring-boot:run
./mvnw -pl resource-server spring-boot:run
./mvnw -pl client spring-boot:run
```

Open <http://localhost:8080/messages> in a browser, sign in as `user` / `password`,
approve consent — the client calls the resource server and shows the JSON plus the
access token it sent.

## Try it without a browser (client_credentials)

```bash
# token for my-client-id, full scope
TOKEN=$(curl -s -u my-client-id:my-client-secret \
  -d grant_type=client_credentials -d scope='message.read message.write' \
  http://localhost:9000/oauth2/token | sed 's/.*"access_token":"//;s/".*//')

curl -s -H "Authorization: Bearer $TOKEN" http://localhost:8090/messages
curl -s -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' \
  -d '{"message":"hello"}' http://localhost:8090/messages

curl -s -o /dev/null -w '%{http_code}\n' http://localhost:8090/messages          # 401
```

A Postman collection + environment covering all of the above lives in
[`../docs`](../docs) ("Jwt Oauth 02" folder of *Spring Security 2026*).
