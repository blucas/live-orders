#!/usr/bin/env groovy

def label = "build-jenkins-operator-${UUID.randomUUID().toString()}"
def home = "/home/jenkins"
def workspace = "${home}/workspace/build-jenkins-operator"
def workdir = "${workspace}/src/github.com/VirtusLab/jenkins-operator/"

podTemplate(label: label,
        containers: [
                containerTemplate(name: 'jnlp', image: 'jenkins/jnlp-slave:alpine'),
                containerTemplate(name: 'jdk-8', image: 'openjdk:8-jdk-alpine', ttyEnabled: true),
        ]) {

    node(label) {
//        dir(workdir) {
            stage('Init') {
                timeout(time: 3, unit: 'MINUTES') {
                    checkout scm
                }
            }

            stage('Build') {

                container('jdk-8') {
                    sh 'gradlew build'
                }
            }

            stage('Test') {
                container('jdk-8') {
                    sh 'gradlew test'
                }
            }
///        }
    }
}
