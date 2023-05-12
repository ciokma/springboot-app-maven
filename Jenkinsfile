
// The name you want to give your Spring Boot application
// Each resource related to your app will be given this name
appName = "java-spring-boot-ms"

pipeline {
    // Use the 'maven' Jenkins agent image which is provided with OpenShift 
    agent any
    environment {
		DOCKERHUB_CREDENTIALS=credentials('dockerhub-cred')
	}
    stages {
    
        stage('Construir aplicación') {
          steps {
            // Puedes elegir Maven o Gradle según tu proyecto
            sh 'mvn clean package'
          }
        }
        stage('Crear imagen Docker') {
            steps {
              sh 'docker build -t ciokma/springboot-cdojo:latest .'
              sh 'docker image save -o ciokma-springboot-latest.tar ciokma/springboot-cdojo:latest'
              
            }
        }
       stage('Login dockerhub') {

			steps {
				sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
			}
		}

		stage('Push to dockerhub') {

			steps {
				sh 'docker push ciokma/springboot-cdojo:latest'
			}
		}
        
        stage('Desplegar en OpenShift') {
            steps {
              sh '''
		    oc login --token=Y1KUsDAJgnc_OmVsyMuS4W3PehLZOgtN51uJgElWPSU --server=https://ec2-100-25-13-67.compute-1.amazonaws.com:8443 --insecure-skip-tls-verify
		    oc new-app ciokma/springboot-cdojo:latest --name springboot-cdojo
		    oc expose svc springboot-cdojo --name=springboot-cdojo
                  '''
             }
        }
    }
}
