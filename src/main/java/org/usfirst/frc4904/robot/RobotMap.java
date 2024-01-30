package org.usfirst.frc4904.robot;

import org.usfirst.frc4904.standard.custom.controllers.CustomCommandJoystick;
import org.usfirst.frc4904.standard.custom.controllers.CustomCommandXbox;
import org.usfirst.frc4904.standard.custom.motorcontrollers.CANTalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import com.kauailabs.navx.frc.AHRS;

//imports for rev robotics neo 550s
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix6.signals.InvertedValue;

//import com.revrobotics.CANSparkMax.IdleMode; //broken
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

// import org.usfirst.frc4904.standard.LogKitten;

import org.usfirst.frc4904.standard.custom.motorcontrollers.CustomCANSparkMax;
import org.usfirst.frc4904.standard.subsystems.motor.SparkMaxMotorSubsystem;
import org.usfirst.frc4904.standard.subsystems.chassis.SwerveDrive;
import org.usfirst.frc4904.standard.subsystems.chassis.SwerveModule;
//import org.usfirst.frc4904.standard.subsystems.chassis.WestCoastDrive;
//import org.usfirst.frc4904.standard.subsystems.motor.TalonMotorSubsystem; //TODO: fix, is required for swerve

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RobotMap {

    public static class Port {

        public static class HumanInput {
            public static final int xyJoystickPort = 0;
            public static final int zJoystickPort = 1;
            public static final int joystick = 2;
        }



        
        // 2023 robot constants // TODO: update ports for swerve
        public static class CANMotor {
            public static final int FRONT_LEFT_DRIVE = 0;
            public static final int FRONT_LEFT_TURN = 1;
            public static final int FRONT_RIGHT_DRIVE = 2;
            public static final int FRONT_RIGHT_TURN = 3;
            public static final int BACK_LEFT_DRIVE = 0;
            public static final int BACK_LEFT_TURN = 1;
            public static final int BACK_RIGHT_DRIVE = 2;
            public static final int BACK_RIGHT_TURN = 3;

        }

        public static class PWM {
        }

        public static class CAN {
        }

        public static class Pneumatics {
        }

        public static class Digital {
        }

       }

    public static class Metrics {


        // // 2023-robot constants
        public static class Chassis {
            public static final double GEAR_RATIO = 496/45; // https://www.desmos.com/calculator/llz7giggcf
            public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(5.12);
            public static final double TRACK_WIDTH_METERS = .8713; // +/- 0.5 inches
            public static final double TRACK_LENGTH_METERS = 1; //TODO: measure this 
            public static final double CHASSIS_LENGTH = Units.inchesToMeters(37); // +/- 0.5 inches
            public static final Translation2d CENTER_MASS_OFFSET = new Translation2d(0,0); // no offset
            public static final double EncoderTicksPerRevolution = 2048;

            //TODO: set 2024 swerve module metrics
            public static final double MAX_SPEED = -1;
            public static final double MAX_ACCELERATION = -1;
            public static final double MAX_TRANSLATION_SPEED = -1;
            public static final double MAX_TURN_SPEED = -1;
            public static final double MAX_TURN_ACCELERATION = -1;
            public static final double DRIVE_GEAR_RATIO = -1;
            public static final double TURN_GEAR_RATIO = -1;
        }
        

    }

    public static class PID {
        public static class Drive {
            // PID constants
            // public static final double kP = 1.5;
            public static final double kP = 3.3016;
            public static final double kI = 0;  // FIXME: tune
            public static final double kD = 0;
            // feedforward constants
            // pre-sfr on-carpet characterization
            // public static final double kS = 0.025236; 
            // public static final double kV = 3.0683;
            // public static final double kA = 0.7358;
            //post sfr characterization
            public static final double kS = 0.12507; 
            public static final double kV = 2.9669;
            public static final double kA = 0.67699;
        }

        public static class Turn { //TODO: tune
            // PID constants
            public static final double kP = 0;
            public static final double kI = 0;
            public static final double kD = 0;
            // feedforward constants
            public static final double kS = 0;
            public static final double kV = 0;
            public static final double kA = 0;
        }
    }

    public static class Component {
        //TODO: turn motors are NOT falcons and so can't use cantalons
        public static CANTalonFX FLdrive;
        public static CustomCANSparkMax FLturn;
        public static CANTalonFX FRdrive;
        public static CustomCANSparkMax FRturn;
        public static CANTalonFX BLdrive;
        public static CustomCANSparkMax BLturn;
        public static CANTalonFX BRdrive;
        public static CustomCANSparkMax BRturn;

        public static SwerveModule FLmodule;
        public static SwerveModule FRmodule;
        public static SwerveModule BLmodule;
        public static SwerveModule BRmodule;

        //encoders are dutycycle encoders, not standard can encoders
        public static DutyCycleEncoder FLturnEncoder;
        public static DutyCycleEncoder FRturnEncoder;
        public static DutyCycleEncoder BLturnEncoder;
        public static DutyCycleEncoder BRturnEncoder;

        public static AHRS navx;

        // public static RobotUDP robotUDP;

        public static SwerveDrive chassis;
    }

    public static class NetworkTables {
        public static NetworkTableInstance instance;

        public static class Odometry {
            public static NetworkTable table;
            public static NetworkTableEntry pose;
            public static NetworkTableEntry accel;
            public static NetworkTableEntry turretAngle;
        }

        public static class Localization {
            public static NetworkTable table;
            public static NetworkTableEntry goalDistance;
            public static NetworkTableEntry goalRelativeAngle;
        }
    }

    public static class Input {
    }

    public static class HumanInput {
        public static class Driver {
            public static CustomCommandXbox xbox;
            public static CustomCommandJoystick xyJoystick;
            public static CustomCommandJoystick turnJoystick;
        }

        public static class Operator {
            public static CustomCommandJoystick joystick;
        }
    }

    public RobotMap() {
        Component.navx = new AHRS(SerialPort.Port.kMXP);

        HumanInput.Driver.xyJoystick = new CustomCommandJoystick(Port.HumanInput.xyJoystickPort, 0.01);
        HumanInput.Driver.xyJoystick = new CustomCommandJoystick(Port.HumanInput.zJoystickPort, 0.01);
	HumanInput.Operator.joystick = new CustomCommandJoystick(Port.HumanInput.joystick, 0.01);
        // // UDP things
        // try {
        //     Component.robotUDP = new RobotUDP(Port.Network.LOCAL_SOCKET_ADDRESS, Port.Network.LOCALIZATION_ADDRESS);
        // } catch (IOException ex) {
        //     LogKitten.f("Failed to initialize UDP subsystem");
        //     LogKitten.ex(ex);
        // }


        /***********************
         * Chassis Subsystem
        *************************/
        
        Component.FLdrive  = new CANTalonFX(Port.CANMotor.FRONT_LEFT_DRIVE);
        Component.FLturn = new CustomCANSparkMax(Port.CANMotor.FRONT_LEFT_TURN, MotorType.kBrushless, false);
        Component.FRdrive  = new CANTalonFX(Port.CANMotor.FRONT_RIGHT_DRIVE);
        Component.FRturn = new CustomCANSparkMax(Port.CANMotor.FRONT_RIGHT_DRIVE, MotorType.kBrushless, false);
        Component.BLdrive  = new CANTalonFX(Port.CANMotor.BACK_LEFT_DRIVE);
        Component.BLturn = new CustomCANSparkMax(Port.CANMotor.BACK_LEFT_TURN, MotorType.kBrushless, false);
        Component.BRdrive  = new CANTalonFX(Port.CANMotor.BACK_RIGHT_DRIVE);
        Component.BRturn = new CustomCANSparkMax(Port.CANMotor.BACK_RIGHT_TURN, MotorType.kBrushless, false);

        //TalonMotorSubsystem rightDriveMotors = new TalonMotorSubsystem("right drive motors", NeutralMode.Brake, 0, Component.frontRightWheelTalon, Component.backRightWheelTalon);
        Translation2d locationFL = new Translation2d(Metrics.Chassis.TRACK_LENGTH_METERS / 2, Metrics.Chassis.TRACK_WIDTH_METERS / 2);
        Translation2d locationFR = new Translation2d(Metrics.Chassis.TRACK_LENGTH_METERS / 2, -(Metrics.Chassis.TRACK_WIDTH_METERS / 2));
        Translation2d locationBL = new Translation2d(-(Metrics.Chassis.TRACK_LENGTH_METERS / 2), Metrics.Chassis.TRACK_WIDTH_METERS / 2);
        Translation2d locationBR = new Translation2d(-(Metrics.Chassis.TRACK_LENGTH_METERS / 2), -(Metrics.Chassis.TRACK_WIDTH_METERS / 2));
        SwerveDriveKinematics kinematics = new SwerveDriveKinematics(locationFL, locationFR, locationBL, locationBR);

        Component.FLmodule  = new SwerveModule(Component.FLdrive, Component.FLturn, Component.FLturnEncoder, locationFL);
        Component.FRmodule = new SwerveModule(Component.FRdrive, Component.FRturn, Component.FRturnEncoder, locationFR);
        Component.BLmodule   = new SwerveModule(Component.BLdrive, Component.BLturn, Component.BLturnEncoder, locationBL);
        Component.BRmodule  = new SwerveModule(Component.BRdrive, Component.BRturn, Component.BRturnEncoder, locationBR);
        SwerveModule[] modules = {Component.FLmodule, Component.FRmodule, Component.BLmodule, Component.BRmodule};

        // SwerveDriveOdometry odometry = new SwerveDriveOdometry(kinematics, new Rotation2d(getHeading()));
        Component.chassis = new SwerveDrive(modules, kinematics, Component.navx, Metrics.Chassis.CENTER_MASS_OFFSET, new Pose2d(0,0,new Rotation2d(0)));


        // Autonomous.autonCommand = Component.chassis.c_buildPathPlannerAuto(
        //     PID.Drive.kS, PID.Drive.kV, PID.Drive.kA,
        //     Autonomous.RAMSETE_B, Autonomous.RAMSETE_ZETA,
        //     Autonomous.AUTON_NAME, Autonomous.MAX_VEL, Autonomous.MAX_ACCEL,
        //     Autonomous.autonEventMap
        // );

}
}