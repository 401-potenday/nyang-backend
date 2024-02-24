pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'potenday-dev'
        ECR_REPOSITORY = '276943215323.dkr.ecr.ap-northeast-2.amazonaws.com'
        TARGET_HOST = "ubuntu@dev-api.itthatcat.xyz"
        SERVER = "https://dev-api.itthatcat.xyz"
    }
  
    stages {
        stage('BE - Test') {
            steps {
                script {
                    sh '''
                        ./gradlew test
                    '''
                }
            }
        }
        stage('BE - Build') {
            steps {
                script {
                    sh '''
                        ./gradlew clean
                        ./gradlew build --exclude-task=test
                    '''
                }
            }
        }
        stage('BE - Docker Build') {
            steps {
                script {
                    def image = docker.build("${env.DOCKER_IMAGE}")
                    env.DOCKER_IMAGE_ID = image.id
                }
            }
        }
        stage('BE - Push Development Image to ECR') {
            when {
                branch 'develop'
            }
            steps {
                script {
                    def image = docker.image("${env.DOCKER_IMAGE_ID}")
                    docker.withRegistry("https://${env.ECR_REPOSITORY}", "ecr:ap-northeast-2:potenday-ecr-credentials") {
                        image.push("${env.BUILD_NUMBER}")
                        image.push("latest")
                    }
                }
            }
        }
        stage("BE - Development Deploy") {
            when {
                branch 'develop'
            }
            steps {
                sshagent(credentials: ['jenkins-deploy-server-credentials']) {
                    sh 'ssh -o StrictHostKeyChecking=no ${TARGET_HOST} '
                    sh "ssh ${TARGET_HOST} 'aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin ${ECR_REPOSITORY}'"
                    sh "ssh ${TARGET_HOST} 'cd /home/kmss69052 && docker compose pull && docker compose up -d'"
                }
            }
        }
    }
}
