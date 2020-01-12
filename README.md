Este repositório tem como objetivo demonstrar testes instrumentados com integração com aws para screenshots e evidências
Os comandos para compilar são:
- ./gradlew :app:packageDebugUniversalApk --stacktrace //este comando usa o nome do módulo principal, portanto o correto, caso o módulo mude será: ./gradlew :<modulo base>packageDebugUniversalApk --stacktrace 
Este comando gera um apk com todos os módulos - mesmo os 'On Demand' já instalados para possibilitar todos os testes
- ./gradlew assembleDebugAndroidTest
Este comando compila os testes instrumentados de todos os módulos
