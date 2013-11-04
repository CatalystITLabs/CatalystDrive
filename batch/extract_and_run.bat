REM This is a batch file to extract and run the server.
REM The directory you are in will be the root directory of the app.
REM
REM   cd\
REM   c:
REM   mkdir [directoryWhereWarShouldBeExtractedAndRun]
REM   cd [directoryWhereWarShouldBeExtractedAndRun]
REM   jar -xvf [yourWARfilePath]
REM   java [fullyQualifiedNameOfEmbeddedJettyServer] [params]
REM
cd\
c:
mkdir runnable-wars
cd runnable-wars
jar -xvf %USERPROFILE%/workspace/CatalystDrive/target/CatalystDrive.war
java com.catalyst.drive.servers.EmbeddedJettyServer -path:CatalystDrive