Team 2393 FRC Season 2024
=========================

Game manual: https://www.firstinspires.org/resource-library/frc/competition-manual-qa-system

Forum: https://www.chiefdelphi.com/, https://www.projectb.net.au/resources/robot-mechanisms

Timeline
--------

* 2024 Beta
  - See https://github.com/wpilibsuite/2024Beta
  - 2024 Beta versions of RoboRio image, WPILib, CTRE lib all "work" in initial tests,
    but command classes are slightly different and new swervelib additions,
    to best start over with new API
  - CTRE offers "Phoenix 5" and "Phoenix 6" API.
    Original Pigeon is only supported with v5.
    New Phoenix X Tuner can still see, update and plot devices with v5 firmware,
    so starting with that to remain compatible with all test hardware.
* January 6: Kickoff
  - Study WPILib manual: https://docs.wpilib.org/en/latest
  - Install WPILib, 3rd party libs (CTRE, Rev)
    + https://github.com/wpilibsuite/allwpilib/releases
    + https://github.com/CrossTheRoadElec/Phoenix-Releases/releases
    + https://store.ctr-electronics.com/software
    + https://www.revrobotics.com/software
    + https://docs.revrobotics.com
    + https://www.andymark.com
    + https://wcproducts.com
* January 13: Start programming on drive chassis
  - Control motors: Set voltage, speed or position
  - Teleop drive: Basic direction and speed, then "swerve"
  - Odometry, simulation
  - Auto drive
  - Prepare for components of actual robot
* February 3: Start programming actual robot
* February 17: Test, tune, practice
* March 3-6: Competition? https://frc-events.firstinspires.org/2024/Events/EventList
* April 3-6: Competition


VS Code Settings
----------------

 * Wpilib: "Skip Select Simulate Extension" to always run SimGUI
 * "Minimap Enabled": De-select to hide

Git Branches
------------

To work on a branch, click on the "main" branch in the bottom left status bar, 
select "Create new Branch" and enter a name like "idea_xyz".

Change source code as desired.
In the Source Control view you can check for changes, and finally commit and push them.
Note that the changes will be pushed on the branch; the usual "push" may appear
renamed into "Publish Branch".

On https://github.com/team2393/frc2024, there will be an option to "Compare & Pull Request".
A pull request can be used to review changes, maybe work on some follow-up to add to the branch.
Finally, the pull request can be "merged".

