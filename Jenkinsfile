def mvn(String args, String dir) {
    sh "mvn ${args} -f ${dir}/pom.xml"
}

def dockerBuildPush(String dir) {
    def app_version = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout -f ${dir}/pom.xml", returnStdout: true).trim()
    def app_name = sh(script: "mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout -f ${dir}/pom.xml", returnStdout: true).trim()
    def image_name = "mouradtals/${app_name}:${app_version}"
    sh "docker build -t ${image_name} ${dir}"
    sh "docker push ${image_name}"
}

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
                mvn 'clean install -DskipTests', 'product_management_system_original'
                mvn 'clean install -DskipTests', 'product_management_system_kafka'
            }
        }

        stage('SonarQube analysis') {
            steps {
                script {
                    def scannerHome = tool 'SonarQube'
                    def sonarAnalysis = { dir ->
                        withSonarQubeEnv('SonarServer') {
                            sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=${dir} -Dsonar.projectName=${dir} -Dsonar.exclusions=**/*.java -f ${dir}/pom.xml"
                        }
                    }
                    sonarAnalysis('product_management_system_original')
                    sonarAnalysis('product_management_system_kafka')
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
                    sh "docker login -u mouradtals -p 4M4n9?MgDbgpd6iD"
                    dockerBuildPush('product_management_system_original')
                    dockerBuildPush('product_management_system_kafka')
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
