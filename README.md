Two-player connection game of Tic Tac Toe  and  Connect Four

### Project Setup and Tools for JavaFX

JavaFX is a user interface library for java. The Scene Builder provides visual editing of FXML files, making it easier to create visual applications.

### IntelliJ JavaFX Project

Instead of creating a "normal" Java project you must create a "JavaFX Application". This will create a sample project with a few pre-configured files that are enough to verify that you have correctly followed the following steps once you are able to start the sample application.

### JavaFX Libraries

To be able to use JavaFX in your JavaFX project, you have to add the JavaFX Library as this is not part of the JDK. To do this, choose **File | Project Structure**.

Open **Libraries** click on the + (top left) and select **From Maven**. Enter the string `org.openjfx:javafx-fxml:11.0.2` into the query box. Then click the button with the magnifying glass icon (🔎) and select the corresponding string from the dropdown list. Ensure that the checkbox **Download to:** is checked. The path must be a folder called lib inside your project directory. Now click OK.

Now the **"Choose Modules" Dialogue** should appear. Your project should be selected by default, so you may just click OK again.

At the end, there should be a total of 8 jar files appearing in the lib folder of your project. You then need to click **Apply**, otherwise the .jar files are downloaded but not referenced from your project.

### JavaFX Runtime Settings

Your application will now compile, but you will get an error message along the lines of `JavaFX runtime components are missing, and are required to run this application` if you try to run it. So we will have to tell the JavaVM where it must look for JavaFX.

In the top menu bar choose `Run | Edit configurations`. Select **Application | Main** on the left. Enter the following in the **VM options** field: `--module-path lib --add-modules javafx.controls,javafx.fxml`

Check if everything works by compiling and running your project under **Run | Run 'Main'**. If you see an empty window titled 'Hello World', it means that you have configured everything correctly, and you're ready to start.

### Scene Builder

The latest version of the Scene Builder can also be downloaded from the Gluon website.

After installing the Scene Builder, open IntelliJ. In the **Settings/Preferences** dialog choose **Languages and Frameworks | JavaFX**. In the field **Path to Scene Builder** specify the path to the Scene Builder executable. Click **Apply** to apply the changes.



If you now want to open .fxml files in the Scene Builder, go to the Scene Builder tab under the .fxml file. If the Scene Builder is not shown in this tab, right-click on the .fxml file and select **Open in SceneBuilder**.

