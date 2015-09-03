@ECHO OFF
REM Avoid useless messages
SET _JAVA_OPTIONS=

REM --------------------------------------------------
SET BYCARPROPLIST=0.05,0.1
SET GUIDEDPROPLIST=0.1,0.9
SET EXPREPET=1
SET GENERPROGOUTPUT=NUL
SET MSGSOUTPUT=NUL
SET SIMULOUTPUT=simuloutput.txt
SET SIMULERROUTPUT=simulerroutput.txt
SET GUIDEDDISTANCES=guideddistances.txt
SET EXPLORERDISTANCES=explorerdistances.txt
SET CUMULGUIDEDDISTANCES=cumulguideddist.txt
SET CUMULEXPLORERDISTANCES=cumulexplorerdist.txt
REM --------------------------------------------------

ECHO Parameters (Description:Name:Values)
ECHO --------------------------------------------------
ECHO Car Proportion Values:		BYCARPROPLIST:	%BYCARPROPLIST%
ECHO Guided Proportion Values:	GUIDEDPROPLIST:	%GUIDEDPROPLIST%
ECHO Experiment Repetitions:		EXPREPET:	%EXPREPET%
ECHO --------------------------------------------------

IF EXIST %SIMULOUTPUT% (
	DEL %SIMULOUTPUT%
)
IF EXIST %SIMULERROUTPUT% (
	DEL %SIMULERROUTPUT%
)
IF EXIST %CUMULGUIDEDDISTANCES% (
	DEL %CUMULGUIDEDDISTANCES%
)
cd. >%CUMULGUIDEDDISTANCES%
IF EXIST %CUMULEXPLORERDISTANCES% (
	DEL %CUMULEXPLORERDISTANCES%
)
cd. >%CUMULEXPLORERDISTANCES%

ECHO -------------------------------------------------- >> %SIMULOUTPUT%
FOR %%C IN (%BYCARPROPLIST%) DO (
	ECHO Creating agent plans file with BYCARPROP of %%C ... > %MSGSOUTPUT%
	java -jar generate.jar master.xml agents.xml %%C > %GENERPROGOUTPUT%
	FOR %%G IN (%GUIDEDPROPLIST%) DO (
		ECHO Creating parameter file with GUIDEDPROP of %%G ... > %MSGSOUTPUT%
		java -jar propfilemodifier.jar %EXPREPET% %%G > %GENERPROGOUTPUT%
		ECHO Running simulator with BYCARPROP of %%C and GUIDEDPROP of %%G ...
		ECHO BYCARPROP = %%C, GUIDEDPROP = %%G >> %SIMULOUTPUT%
		ECHO -------------------------------------------------- >> %SIMULOUTPUT%
		java -jar simulator.jar >> %SIMULOUTPUT% 2>> %SIMULERROUTPUT%
		ECHO Coping distances result files ... > %MSGSOUTPUT%
		copy /b %CUMULGUIDEDDISTANCES%+%GUIDEDDISTANCES%=%CUMULGUIDEDDISTANCES% > %MSGSOUTPUT%
		copy /b %CUMULEXPLORERDISTANCES%+%EXPLORERDISTANCES%=%CUMULEXPLORERDISTANCES% > %MSGSOUTPUT%
	)
)
ECHO -------------------------------------------------- >> %SIMULOUTPUT%

ECHO All Done!

REM --------------------------------------------------
