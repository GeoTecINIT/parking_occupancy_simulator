REM --------------------------------------------------
REM Avoid useless messages
@ECHO OFF
SET _JAVA_OPTIONS=

REM --------------------------------------------------
SET BYCARPROPLIST=0.3,0.5,0.7
SET GUIDEDPROPLIST=0.25,0.5,0.75
SET EXPREPET=3
SET GENERPROGOUTPUT=NUL
SET MSGSOUTPUT=NUL
SET SIMULOUTPUT=simuloutput.txt
REM --------------------------------------------------

ECHO Settings: BYCARPROPLIST=%BYCARPROPLIST% GUIDEDPROPLIST=%GUIDEDPROPLIST% EXPREPET=%EXPREPET%

IF EXIST %SIMULOUTPUT% (
	DEL %SIMULOUTPUT%
)

FOR %%C IN (%BYCARPROPLIST%) DO (
	ECHO Creating agent plans file with BYCARPROP of %%C ... > %MSGSOUTPUT%
	java -jar generate.jar master.xml agents.xml %%C > %GENERPROGOUTPUT%
	FOR %%G IN (%GUIDEDPROPLIST%) DO (
		ECHO Creating parameter file with GUIDEDPROP of %%G ... > %MSGSOUTPUT%
		java -jar propfilemodifier.jar %EXPREPET% %%G > %GENERPROGOUTPUT%
		ECHO Running simulator with BYCARPROP of %%C and GUIDEDPROP of %%G ...  
		java -jar simulator.jar >> %SIMULOUTPUT%
	)
)

ECHO All Done!

REM --------------------------------------------------
