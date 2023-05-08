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
               script{
                def scannerHome = tool 'SonarQube';
                dir('product_management_system_original') {
                    withSonarQubeEnv('SonarServer') {
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=pms_original -Dsonar.projectName='pms_original' -Dsonar.exclusions=**/*.java"
                    }
                  }
                 
                dir('product_management_system_kafka') {
                    withSonarQubeEnv('SonarServer') {
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=pms_kafka -Dsonar.projectName='pms_kafka' -Dsonar.exclusions=**/*.java"
                    }
                }
               }
            }
        }

        stage('Wait for SonarQube analysis to complete') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }

        stage('Build Docker images') {
            steps {
                script {
                    dir('product_management_system_original') {
                        def app_version_original = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                        def app_name_original = sh(script: "mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout", returnStdout: true).trim()
                        def image_name_original = "mouradtals/${app_name_original}:${app_version_original}"
                        sh "docker build -t ${image_name_original} ."
                        sh "docker login -u mouradtals -p 4M4n9?MgDbgpd6iD"
                        sh "docker push ${image_name_original}"
                    }

                    dir('product_management_system_kafka') {
                        def app_version_kafka = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                        def app_name_kafka = sh(script: "mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout", returnStdout: true).trim()
                        def image_name_kafka = "mouradtals/${app_name_kafka}:${app_version_kafka}"
                        sh "docker build -t ${image_name_kafka} ."
                        sh "docker login -u mouradtals -p 4M4n9?MgDbgpd6iD"
                        sh "docker push ${image_name_kafka}"
                    }
                }
            }
        }
        
        
        stage('Deploy using Docker Compose') {
            steps {
                sh "docker-compose -f ${env.WORKSPACE}/docker-compose.yml up -d"
            }
        }
        
        
       
    }
}
