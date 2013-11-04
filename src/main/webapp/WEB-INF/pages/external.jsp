<p>To make a war for deploying on an external container like Tomcat:</p>
<ol>
	<li>Go to Run As... -&gt; Maven clean.  This will remove temporary BIRT folders as well as clean the target directory.</li>
	<li>Go to Run -&gt; Run configurations -&gt; run "CatalystTemplate - Create War for External Container"</li>
	<li>Go to the target directory of the project, refresh the folder and copy the CatalystTemplate.war file
	to wherever the container needs the war.  E.G. for Tomcat, in the webapp directory of the Tomcat
	installation.</li>
</ol>
	