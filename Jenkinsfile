pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Viettranni/aikido_inclass.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}
