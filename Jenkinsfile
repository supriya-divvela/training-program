pipeline {
    agent any
    tools{
        maven 'Maven 3.8.7'
    }
    stages {
        stage('SCM') {
            steps {
               git branch: 'main', url: 'https://gitlab.com/supriyadivvela9848/training-program.git'
            }
        }
        stage('Build and Test') {
            steps {
                bat "cd TrainingProgram && mvn clean package"
            }
        }
        stage('jacoco code coverage'){
            steps{
                jacoco()
            }
        }
         stage('SonarQube analysis') {
            steps {
                bat "cd TrainingProgram && mvn sonar:sonar -Dsonar.projectKey=sonar -Dsonar.projectName='sonar' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_6dd5152ad1d241c193a513a12e8dbf7df617a567"
            }
        }
        stage('Push image to Hub'){
            steps{
                withCredentials([string(credentialsId: 'ok', variable: 'ok')]) {
                    bat "docker login -u supriyadivvela -p ${ok}"
                    bat "docker push com.epam/training-program:0.0.1-SNAPSHOT"
                }
            }
        }
    }
}