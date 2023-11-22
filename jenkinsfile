pipeline {
	agent any

	tools {
		jdk 'jdk21'
	}

	stages {
		stage('Build') {
			steps {
				sh ./gradlew test
			}
		}
	}
}
