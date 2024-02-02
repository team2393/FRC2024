Team 2393 FRC Season 2024
=========================

Game manual: https://www.firstinspires.org/resource-library/frc/competition-manual-qa-system

Software manual: https://docs.wpilib.org/en/stable/
- See "Step 2: Installing Software",
  - "WPILib Installation Guide" for development tools
  - "Installing the FRC Game Tools" for driver station
- Expect both to have updates, you usually need the latest

Forums: https://www.chiefdelphi.com/, https://www.projectb.net.au/resources/robot-mechanisms


Timeline
--------

* Notes from 2024 Beta
  - See https://github.com/wpilibsuite/2024Beta
  - 2024 Beta versions of RoboRio image, WPILib, CTRE lib all "work" in initial tests,
    but command classes are slightly different and new swervelib additions,
    to best start over with new API
  - CTRE offers "Phoenix 5" and "Phoenix 6" API.
    Original Pigeon is only supported with v5.
    New Phoenix X Tuner can still see, update and plot devices with v5 firmware,
    so starting with that to remain compatible with all test hardware.
* January 6: Kickoff, https://www.tnfirst.org/frc-events
  - Install WPILib, 3rd party libs (CTRE, Rev)
    + https://github.com/wpilibsuite/allwpilib/releases
    + https://github.com/CrossTheRoadElec/Phoenix-Releases/releases
    + https://store.ctr-electronics.com/software
    + https://www.revrobotics.com/software
    + https://docs.revrobotics.com
    + https://www.andymark.com
    + https://wcproducts.com
    + https://docs.photonvision.org
    + https://limelightvision.io
  - How to run Simulation
  - Mechanism display
* January 13: Start programming on "Swervebot"
  - Robot base class
  - Driver station
  - Field Management System
  - Control motors: Set voltage, speed or position
  - Swervebot skeleton
* January 20 Status
  - Command intro using LEDs
  - Camera intro
  - Use 2D camera data for alignment
* Next
  - Commands to drive
  - Auto drive
    - https://www.youtube.com/shorts/WaQLQT_cJeY
  - Prepare for components of actual robot
  - Get/prepare laptop
  - Prepare spare RoboRIO, power distribution hub, speed controllers, ...
  - Prepare button board
* February 3: Start programming actual robot
* February 17: Test, tune, practice
* March 3-6: Competition? https://frc-events.firstinspires.org/2024/Events/EventList, https://www.tnfirst.org/frc-events
* April 3-6: Competition?


Software Setup
--------------

 * Install "WPILib", https://docs.wpilib.org/en/latest/docs/zero-to-robot/step-2/wpilib-setup.html
 * Install git. For Windows, use https://git-scm.com/. On a Mac, run `git` once from the terminal to trigger installation
 * Get a copy of the sources:
   * Start the 2024 WPI Lib version of VS Code
   * Menu "View", "Command Palette", "Git: Clone"
   * Enter repository URL `https://github.com/team2393/FRC2024.git`
   * As a destination, best create a folder "git" in your home directory,
     so the sources will end up in something like `/Users/YourName/git/FRC2024`.

During the initial "git clone", VS Code will end up opening the folder automatically.
To get there later on, you can use the VS Code menu "File", "Open Folder" and then select
`/Users/YourName/git/FRC2024`.


VS Code Settings
----------------

Suggestions for File, Preferences, Settings:
 * Wpilib: "Skip Select Simulate Extension" to always run SimGUI
 * "Minimap Enabled": De-select to hide

Suggestions for View, Extensions:
 * Install "Git Graph"

Git Branches
------------

To work on a branch in VS Code, click on the "main" branch in the bottom left status bar, 
select "Create new Branch" and enter a name like "idea_xyz".

Change source code as desired.
In the Source Control view you can check for changes, and finally commit and push them.
Note that the changes will be pushed on the branch; the usual "push" may appear
renamed into "Publish Branch".

On https://github.com/team2393/frc2024, there will be an option to "Compare & Pull Request".
A pull request can be used to review changes, maybe work on some follow-up to add to the branch.
Finally, the pull request can be "merged".

In VS Code, click on the branch name in the bottom left status bar,
select the "main" branch again, then "Pull" from the Source Control view.


PhotonVision Camera
-------------------

Image Raspberry Pi with PhotonVision. Bootup with HDMI monitor connected to observe initial self-configuration.
Reboot on robot network and navigate web browser on Laptop to http://photonvision.local:5800

**Settings, Networking**

- Set Team Number to 2393.
- Change IP Assignment Mode from DHCP to Static, set address to 10.23.93.12.
  (Static 10.TE.AM.6-19 are general purpose. 10.TE.AM.11 is often used for camera, leave that for Limelight)
- Press SAVE

From now on, access camera via http://10.23.93.12:5800

**Settings for Pipeline of Type AprilTag**

AprilTag:
 - Family 36h11 (6.5in)

Input:
- Resolution 320x180 MJPEG
- Exposure: Somewhere below 20 results in 30fps. Much lower doesn't see target, higher reduces the FPS.

