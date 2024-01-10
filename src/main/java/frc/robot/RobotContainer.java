// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.swervedrive.auto.Autos;
// import frc.robot.commands.swervedrive.drivebase.AbsoluteDrive;
// import frc.robot.commands.swervedrive.drivebase.AbsoluteFieldDrive;
// import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.Secondary.ProtoMotoA;
import frc.robot.subsystems.Secondary.ProtoMotoB;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve/neo"));
  // CommandJoystick rotationController = new CommandJoystick(1);
  // Replace with CommandPS4Controller or CommandJoystick if needed
  CommandJoystick driverController = new CommandJoystick(1);

  // CommandJoystick driverController   = new CommandJoystick(3);//(OperatorConstants.DRIVER_CONTROLLER_PORT);
  XboxController driverXbox = new XboxController(0);
  ProtoMotoA protoMotoA = new ProtoMotoA();
  ProtoMotoB protoMotoB = new ProtoMotoB();  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Configure the trigger bindings
    configureBindings();

    // AbsoluteDrive closedAbsoluteDrive = new AbsoluteDrive(drivebase,
    //                                                       // Applies deadbands and inverts controls because joysticks
    //                                                       // are back-right positive while robot
    //                                                       // controls are front-left positive
    //                                                       () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
    //                                                                                    OperatorConstants.LEFT_Y_DEADBAND),
    //                                                       () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
    //                                                                                    OperatorConstants.LEFT_X_DEADBAND),
    //                                                       () -> -driverXbox.getRightX(),
    //                                                       () -> -driverXbox.getRightY());

    // AbsoluteFieldDrive closedFieldAbsoluteDrive = new AbsoluteFieldDrive(drivebase,
    //                                                                      () ->
    //                                                                          MathUtil.applyDeadband(driverXbox.getLeftY(),
    //                                                                                                 OperatorConstants.LEFT_Y_DEADBAND),
    //                                                                      () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
    //                                                                                                   OperatorConstants.LEFT_X_DEADBAND),
    //                                                                      () -> driverXbox.getRawAxis(2));

    // AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(drivebase,
    //                                                                   () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
    //                                                                                             OperatorConstants.LEFT_Y_DEADBAND),
    //                                                                   () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
    //                                                                                               OperatorConstants.LEFT_X_DEADBAND),
    //                                                                   () -> MathUtil.applyDeadband(driverXbox.getRightX(),
    //                                                                                               OperatorConstants.RIGHT_X_DEADBAND), 
    //                                                                   driverXbox::getYButtonPressed, 
    //                                                                   driverXbox::getAButtonPressed, 
    //                                                                   driverXbox::getXButtonPressed, 
    //                                                                   driverXbox::getBButtonPressed);

    TeleopDrive simClosedFieldRel = new TeleopDrive(drivebase,
                                                    () -> MathUtil.applyDeadband(-driverXbox.getLeftY(),
                                                                                 OperatorConstants.LEFT_Y_DEADBAND),
                                                    () -> MathUtil.applyDeadband(-driverXbox.getLeftX(),
                                                                                 OperatorConstants.LEFT_X_DEADBAND),
                                                    () -> -driverXbox.getRightX(), () -> true);
    TeleopDrive closedFieldRel = new TeleopDrive(
        drivebase,
        () -> MathUtil.applyDeadband(-driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(-driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> -driverXbox.getRightX(), () -> true);

    //drivebase.setDefaultCommand(!RobotBase.isSimulation() ? closedAbsoluteDrive : closedFieldAbsoluteDrive);
    drivebase.setDefaultCommand(!RobotBase.isSimulation() ? closedFieldRel : simClosedFieldRel);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    //Button 1 is "A" on xbox controller
    //Button 2 is "B" on xbox controller
    //Button 3 is "X" on xbox controller  
    //Button 4 is "Y" on xbox controller
    //Button 5 is "Left Bumper" on xbox controller
    //Button 6 is "Right Bumper" on xbox controller
    //Button 7 is "Back" on xbox controller
    //Button 8 is "Start" on xbox controller
    //Button 9 is "Left Joystick" on xbox controller
    //Button 10 is "Right Joystick" on xbox controller
    //Axis 0 is left joystick x side to side
    //Axis 1 is left joystick y forward and back
    //Axis 2 is left trigger 
    //Axis 3 is right joystick x side to side
    //Axis 4 is right joystick y forward and back
    //Axis 5 is right trigger

    new JoystickButton(driverXbox, 10).onTrue((new InstantCommand(drivebase::zeroGyro)));
    //new JoystickButton(driverXbox, 3).onTrue(new InstantCommand(drivebase::addFakeVisionReading));
    //new JoystickButton(driverXbox, 3).whileTrue(new RepeatCommand(new InstantCommand(drivebase::lock, drivebase)));
    new JoystickButton(driverXbox,1 ).whileTrue(protoMotoA.ProtoMotoACmd(1)); //1 is forward full speed
    new JoystickButton(driverXbox,1 ).onFalse(protoMotoA.ProtoMotoACmd(0)); //0 is stop
    new JoystickButton(driverXbox,2 ).whileTrue(protoMotoA.ProtoMotoACmd(-1)); //1 is reverse full speed
    new JoystickButton(driverXbox,2 ).onFalse(protoMotoA.ProtoMotoACmd(0)); //0 is stop

    new JoystickButton(driverXbox,3 ).whileTrue(protoMotoA.ProtoMotoACmd(1)); //1 is forward full speed
    new JoystickButton(driverXbox,3 ).onFalse(protoMotoA.ProtoMotoACmd(0)); //0 is stop
    new JoystickButton(driverXbox,4 ).whileTrue(protoMotoA.ProtoMotoACmd(-1)); //1 is reverse full speed
    new JoystickButton(driverXbox,4 ).onFalse(protoMotoA.ProtoMotoACmd(0)); //0 is stop


  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // An example command will be run in autonomous
    return Autos.exampleAuto(drivebase);
  }

  public void setDriveMode()
  {
    //drivebase.setDefaultCommand();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
