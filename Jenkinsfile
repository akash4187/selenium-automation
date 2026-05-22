pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK8'
    }

    parameters {
        choice(name: 'TEST_SUITE', choices: ['testng.xml', 'testng-cucumber.xml'], description: 'Select test suite to run')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser')
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile test-compile -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn test -DsuiteXmlFile=${params.TEST_SUITE} -Dbrowser=${params.BROWSER} -Dheadless=true -Dmaven.test.failure.ignore=true"
            }
        }
    }

    post {
        always {
            // Publish TestNG Results
            testNG(reportFilenamePattern: '**/target/surefire-reports/testng-results.xml',
                   showFailedBuilds: true)

            // Publish Extent Report
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report',
                reportTitles: 'Automation Exercise - Extent Report'
            ])

            // Publish Cucumber Report
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output',
                reportFiles: 'cucumber-report.html',
                reportName: 'Cucumber Report',
                reportTitles: 'Automation Exercise - Cucumber Report'
            ])

            // Archive test artifacts
            archiveArtifacts artifacts: 'test-output/**/*', allowEmptyArchive: true

            // Clean workspace
            cleanWs()
        }

        success {
            echo 'All tests passed successfully!'
        }

        failure {
            echo 'Some tests failed. Check the reports for details.'
        }
    }
}
