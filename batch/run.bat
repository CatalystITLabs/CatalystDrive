REM This is a batch file to extract and run the server.
REM You should be in the root directory of extracted .war when running as below.
REM This assumes you have already extracted the .war in c:\runnable-wars
REM
REM   cd\
REM   c:
REM   cd [directoryWhereInstallationIs]
REM   java [fullyQualifiedNameOfEmbeddedJettyServer] [params]
REM
cd\
c:
cd runnable-wars
java com.catalyst.drive.servers.EmbeddedJettyServer -path:CatalystDrive