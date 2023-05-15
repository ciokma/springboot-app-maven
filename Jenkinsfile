
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
        stage('Upload to Nexus') {
	  steps {
	    nexusArtifactUploader artifacts: [[artifactId: '01-mobile-android', classifier: '', file: 'app/build/outputs/apk/debug/app-debug.apk', type: 'apk']], credentialsId: 'nexus-credentials', groupId: 'android-mobile', nexusUrl: 'http://54.147.37.68:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'android-mobile-app', version: '${currentBuild.number}-INITIAL'
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
		    oc login --token=kMpcdg7ThlPQo5tkaC-9gDEXI7F_AO-6l8BLiWt7wXQ --server=https://ec2-54-224-88-191.compute-1.amazonaws.com:8443 --insecure-skip-tls-verify
		    oc new-app ciokma/springboot-cdojo:latest --name springboot-ms
		    oc expose svc/springboot-ms --name=springboot-ms
                  '''
             }
        }
    }
}
