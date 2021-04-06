pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr:'5'))
        disableConcurrentBuilds()
        timeout(time: 30, unit: 'MINUTES')
    }

    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('perparation') {
            steps {
                sh '''
                mvn --version
                docker --version
                sonar-scanner --version
                '''
            }
        }


        stage('Prettier: Check code format') {
            steps {
                    dir('Car_Rental_WS'){
                        //sh 'mvn prettier:check' https://github.com/HubSpot/prettier-maven-plugin
                        sh 'mvn compile'
                        sh 'echo works'
                    }
            }
        }

        stage('Sonar: Quality code check') {
            steps {
                    sh 'sonar-scanner'
                }
          }
/*
        stage('unit tests') {
            steps {
                    dir('Car_rental_WS'){
                        sh 'mvn test'
                    }
            }
        }
*/

        stage('approval') {
            when{
                allOf{
                    expression {
                        return env.GIT_BRANCH == "main"
                    }
                }
            }
            steps {
                script {
                    timeout(time: 1, unit: 'MINUTES') {
                        input(id: 'Deploy Gate', message: 'Deploy the builded app?', ok: 'Deploy')
                    }
                }
            }
        }

       stage('deploy: app') {
            when{
                allOf{
                    expression {
                        return env.GIT_BRANCH == "main"
                    }
                }
            }
            steps {
                    dir('Docker'){
                        sh 'docker-compose down'
                        sh 'docker-compose up -d --build'
                }
            }
        }
    }
}
