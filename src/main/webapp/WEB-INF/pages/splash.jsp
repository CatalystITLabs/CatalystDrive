<h4>
TILE FOR THE SPLASH PAGE -- This application is a template using 
Spring MVC, Apache Tiles, and Spring Security with LDAP authorization.
Additionally, while it debugs with the jetty server plugin, it has
an embedded Jetty server that makes it portable to run anywhere
there is a JVM.  To make a self-running war, simply select the project
and right click -&gt; run as... -&gt; maven install.  In the target directory
will appear the portable, self-running .war file.  
</h4>
<h5>
Altering the name of this application can be done in a few
easy steps.  First, right click on the project and rename it.
Second, in the project explorer choose the packages to be displayed
hierarchically (down arrow, upper right corner).  Right click
on the top level package and choose refactor -&gt; rename.  Make sure
to check all the boxes in the prompt that appears.  Third, go to
the pom file.  Near the top of the file rename GroupId, ArtifactId, and 
in the properties right below those, rename the embedded server path
to match the new package name.  Finally, scroll down to the Jetty
Plugin area and rename the context path.  You may want to change the
supplied run configurations also.  To do this, go to Run -&gt; 
Run Configurations.  Click on each of the 3 run configurations associated
with this project and change the names and paths accordingly.
</h5>