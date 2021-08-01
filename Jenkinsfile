pipeline {
    agent any
    
    tools {
        maven 'M3'
    }
    
    stages {
        stage('Cleaning workspace'){
            steps {
                sh 'echo "---=--- Cleaning Stage ---=---"'
                script {
                    try {
                        sh 'docker stop projet1 && docker rm projet1'
                    } catch (Exception e) {
                        sh 'echo "---=--- No container to remove ---=---"'
                    }                    
                }
            }
        }
        stage('Checkout'){
            steps {
                sh 'echo "---=--- Checkout ---=---"'
                git branch: 'main', url: 'https://github.com/Mehenni76/projet1.git'
            }
        }
        stage('Clean'){
            steps {
                sh 'echo "---=--- clean ---=---"'
                sh 'mvn clean'
            }
        }
        stage('Compile'){
            steps {
                sh 'echo "---=--- Compile ---=---"'
                sh 'mvn compile'
            }
        }
        stage('Test'){
            steps {
                sh 'echo "---=--- Test ---=---"'
                sh 'mvn test'
            }
        }
        stage('Package'){
            steps {
                sh 'echo "---=--- Package ---=---"'
                sh 'mvn package -DskipTests'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            } 
        }
    stage('SSH transfert') {
        steps {
            script {
                sshPublisher(publishers: [
                    sshPublisherDesc(configName: 'ec2-host2', transfers:[
                        sshTransfer(
                          execCommand: '''
                                echo "-=- Cleaning project -=-"
                                sudo docker stop projet1  || true
                                sudo docker rm projet1 || true
                                sudo docker rmi mdjadda/projet1:1.0 || true
                            '''
                        ),
                        sshTransfer(
                            sourceFiles:"target/projet1.jar",
                            removePrefix: "target",
                            remoteDirectory: "//home//ec2-user",
                            execCommand: "ls /home/ec2-user"
                        ),
                        sshTransfer(
                            sourceFiles:"Dockerfile",
                            removePrefix: "",
                            remoteDirectory: "//home//ec2-user",
                            execCommand: '''
                                cd //home//ec2-user;
                                sudo docker build -t mdjadda/projet1:1.0 .; 
                                sudo docker run -d --name projet1 -p 8085:8085 mdjadda/projet1:1.0;
                            '''
                        )
                    ])
                ])                
            }
        }
    }
    }
}