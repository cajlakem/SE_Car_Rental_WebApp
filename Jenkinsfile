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


        stage('Prettier:Check code format') {
            steps {
                    dir('Car_Rental_WS'){
                        //sh 'mvn prettier:check' https://github.com/HubSpot/prettier-maven-plugin
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
        stage('build: backend') {
            steps {
                sh 'docker build -t lulzimbulica/car_rental_java_backend Car_Rental_WS/'
            }
        }

        stage('build: frontend') {
            steps {
                sh 'docker build -t lulzimbulica/car_rental_angluar_frontend CarRentalAngularApp/'
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
/*
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
