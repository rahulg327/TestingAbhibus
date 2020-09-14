#!groovy

REPORT_PATH = "src/main/java/com/abhibus/qa/utilities/ExtentReportListener/ExtentReporterNG.java"
node {
    agent any
    result = "SUCCESS"
    testerId = "jenkins"
    testerName = "jenkins"

    stage('Set Build Display Name') {
        currentBuild.displayName = "#${BUILD_NUMBER}-REGRESSION"
        echo "Building the Code.........."
    }

    stage('Checkout Source Code') {
        git changelog: false, url: 'https://github.com/rahulg327/TestingAbhibus.git', branch: '${BRANCH}'
    }

    stage('build user') {
        echo "build user.........."
    }
    try {
        stage('Compile and Execute Tests') {
            MVN_BUILD_CMD = "${env.M3}/bin/mvn clean -DEnvironment=${env.ENVIRONMENT} -DHostName=${env.HOSTNAME} -DBrowserName=${env.BROWSER_TYPE} -DDriverType=${env.DRIVER_TYPE} -DRallyUpdate=${env.RALLY_UPDATE} -DTestSet=${env.TEST_SET} -DTesterId=${testerId} -DBuildTag=\"${env.BUILD_TAG}\" test"
            echo "MVN_CMD=${MVN_BUILD_CMD}"
            echo "HOSTNAME=${HOSTNAME}"
            sh MVN_BUILD_CMD
        }
    } catch (caughtError) {
        echo "Error has occurred: ${caughtError}"
        err = caughtError
        result = "FAILURE"
    }

    stage('Publish HTML reports') {
        publishHTML([
                allowMissing         : true,
                alwaysLinkToLastBuild: true,
                keepAll              : true,
                reportDir            : '',
                reportFiles          : "${REPORT_PATH}",
                reportName           : 'AAS test results',
                reportTitles         : ''
        ])
    }
    stage('email test results') {
        def jobName = env.JOB_NAME.substring(env.JOB_NAME.lastIndexOf("/") + 1)
        def subject = "AAS test results"
        def summary =

                """<!DOCTYPE html>
        <style>
        body, table, td, th, p {
          font-family:Verdana,Helvetica,sans serif;
          font-size:12px;
          color:black;
        }
        table { border-collapse: collapse }
        th, td { text-align: left; border: 1px solid #d4d4d4; padding: 6px }
        th { background-color: #f1f1f1; font-weight: bold }
        td.FAILURE { color:white; background-color:#C00000; font-size:100%; font-weight: bold }
        td.SUCCESS{ color:white; background-color:#25a83a; font-size:100%; font-weight: bold }
        td.uppercase{ text-transform: uppercase; }
        </style>
        <body>
            <table>
                <tr>
                    <th>Build URL</th><td><a href=${BUILD_URL}>Build URL</a></td>
                </tr>
                <tr>
                    <th>Environment</th><td>${env.ENVIRONMENT}</td>
                </tr>
                <tr>
                    <th>Hostname</th><td>${env.HOSTNAME}</td>
                </tr>
                <tr>
                    <th>Driver</th><td>${env.DRIVER_TYPE}</td>
                </tr>
                <tr>
                    <th>Browser</th><td>${env.BROWSER_TYPE}</td>
                </tr>
                <tr>
                    <th>Test Report</th><td><a href=${BUILD_URL}/execution/node/3/ws/target/cucumber-reports/ExtentCucumberReport.html>Test Report</a></td>
                </tr>
                <tr>
                    <th>Build Tag</b></th><td>${env.BUILD_TAG}</td>
                </tr>
                <tr>
                    <th>Result</b></th><td>${result}</td>
                </tr>
                <tr>
                    <th>Tester</b></th><td>${testerName}</td>
                </tr>
            </table>
                <br/>
                 <p><i>This report is for internal use only and not to be distributed</i></p>
                 <br/>
                  <p>AAS Test Automation</p>
        </body>"""

        emailext(
                to: 'rahulnetha619@gmail.com',
                subject: subject,
                body: summary,
                attachmentsPattern: '**/ExtentCucumberReport.html',
                mimeType: 'text/html'
        )
    }

}