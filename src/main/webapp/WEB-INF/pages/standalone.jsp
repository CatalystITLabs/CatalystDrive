<p>To deploy with standalone jetty server:</p>
<ol> 
	<li>Go to Run As... -&gt; Maven clean.  This will remove temporary BIRT folders as well as clean the target directory.</li>
	<li>Go to Run -&gt; Run configurations -&gt; run "CatalystTemplate - Create War for Standalone Jetty Server"</li>
	<li>Go to the target directory of the project, refresh the folder and copy the CatalystTemplate.war file
	to c:\runnable-wars</li>
	<li>In the "batch" folder, right-click on extract_and_run.bat -&gt; Open with... -&gt; System Editor*</li>
	<li>The .war will be extracted and the server will start up.</li>
</ol>
<p>* To edit .bat files in Eclipse you must right-click on extract_and_run.bat -&gt; Open with... -&gt; Text Editor</p>