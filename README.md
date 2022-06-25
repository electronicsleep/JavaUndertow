# JavaUndertow

Example microservice in Java and Undertow

Run Test Database
```
git clone https://github.com/electronicsleep/mysql-docker-test.git && cd mysql-docker-test
bash run.sh
```

Set Overrides Properties (src/main/resources/overrides.properties)
```
datasource_connection=jdbc:mysql://127.0.0.1:3306/infradb?useSSL=true&serverTimezone=UTC
datasource_password=password
datasource_user=infradb
```

Run
```
bash run.sh
bash src/tests/test-post.sh
```

http://undertow.io

https://github.com/undertow-io/undertow
