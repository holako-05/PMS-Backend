pipeline { 
    agent any

    tools {
        maven 'MAVEN_HOME'
    }

    stages {
        stage('Pull code from repository') {
            steps {
                checkout scm
            }
        }

        stage('Build project') {
            steps {
                dir('product_management_system_original') {
                    sh 'mvn clean install -DskipTests'
                }
                dir('product_management_system_kafka') {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }

        stage('SonarQube analysis') {
           steps {
                dir('product_management_system_original') {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
                    }
                }
                dir('product_management_system_kafka') {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
                    }
                }
            }
        }

        stage('Wait for SonarQube analysis to complete') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }

        stage('Build Docker image') {
            steps {
                script {
                    def app_version = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                    def app_name = sh(script: "mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout", returnStdout: true).trim()
                    def image_name = "your_dockerhub_user/${app_name}:${app_version}"

                    sh "docker build -t ${image_name} ."
                    sh "docker login -u your_dockerhub_user -p your_dockerhub_password"
                    sh "docker push ${image_name}"
                }
            }
        }

       
    }
}
