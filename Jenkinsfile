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
                '''
            }
        }


        stage('Check code format') {
            steps {
                    dir('Car_Rental_WS'){
                        //sh 'mvn prettier:check' https://github.com/HubSpot/prettier-maven-plugin
                        sh 'echo works'
                    }
            }
        }


        stage('code quality') {
            steps {
                    dir('vaccathon-rest-api'){
                        sh 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
                    }
                }
          }
/*
        stage('unit tests') {
            steps {
                    dir('vaccathon-rest-api'){
                        sh 'mvn test'
                    }
            }
        }

        stage('build: backend') {
            steps {
                sh 'docker build -t lulzimbulica/vaccathon-frontend vaccathon-rest-api/'
            }
        }

        stage('build: frontend') {
            steps {
                sh 'docker build -t lulzimbulica/vaccathon-frontend frontend/vaccathon'
            }
        }

        stage('approval') {
            when{
                allOf{
                    expression {
                        return env.GIT_BRANCH == "origin/main"
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

        stage('push: docker images') {
            when{
                allOf{
                    expression {
                        return env.GIT_BRANCH == "origin/main"
                    }
                }
            }
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub',
                                     usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                    sh '''
                    docker login -u $USERNAME -p $PASSWORD
                    docker push lulzimbulica/vaccathon-frontend
                    docker push lulzimbulica/vaccathon-backend
                    '''
                }
            }
        }
*/
       stage('deploy: app') {
            when{
                allOf{
                    expression {
                        return env.GIT_BRANCH == "main"
                    }
                }
            }
            steps {
                sh '''
                echo test'''
            }
        }
    }
}
