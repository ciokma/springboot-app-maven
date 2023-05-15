
// The name you want to give your Spring Boot application
// Each resource related to your app will be given this name
appName = "java-spring-boot-ms"

pipeline {
    // Use the 'maven' Jenkins agent image which is provided with OpenShift 
    agent any
    environment {
		DOCKERHUB_CREDENTIALS=credentials('dockerhub-cred')
	    	NEXUSDOCKER_CREDENTIALS=credentials('nexusdocker-cred')
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
        stage('Upload Artifact to Nexus') {
	  steps {
   		   nexusArtifactUploader artifacts: [[artifactId: '01-spring-app',
					       classifier: '',
					       file: 'target/spring-app-0.0.1-SNAPSHOT.jar',
					       type: 'jar']],
		    credentialsId: 'nexus-credentials',
		    groupId: 'spring-app',
		    nexusUrl: '54.147.37.68:8081',
		    nexusVersion: 'nexus3',
		    protocol: 'http',
		    repository: 'spring-ms-app',
		    version: "${currentBuild.number}"		  
	  }
       }
        stage('Build and Push Docker Image to Nexus') {
	  steps {
		  sh 'docker login -u $NEXUSDOCKER_CREDENTIALS_USR -p $NEXUSDOCKER_CREDENTIALS_PSW ec2-54-147-37-68.compute-1.amazonaws.com:8085'
		  sh "docker build . -t ec2-54-147-37-68.compute-1.amazonaws.com:8085/springboot:${currentBuild.number}"
		  sh "docker push ec2-54-147-37-68.compute-1.amazonaws.com:8085/springboot:${currentBuild.number}"
		  
	  }
       }
       stage('Login to dockerhub') {

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
		    oc delete all -l app=springboot-ms
		    # oc new-app ciokma/springboot-cdojo:latest --name springboot-ms
		    oc new-app ec2-54-147-37-68.compute-1.amazonaws.com:8085/springboot:${currentBuild.number} --name springboot-ms
		    oc expose svc/springboot-ms --name=springboot-ms
                  '''
             }
        }
    }
}
