@echo off
set "CUSTOM_CSS=src\test\resources\custom-style.css"
REM Use the path you found, e.g.:
set "CORE_CSS=target\site\serenity\css\core.css"

REM Run tests and generate reports
mvn verify -ntp -Dtest=TestRunner -Dcucumber.filter.tags="@US_RDTBS"
mvn serenity:aggregate -Dtags="@US_RDTBS"

REM Copy custom CSS over actual core.css
if exist "%CUSTOM_CSS%" (
    if exist "%CORE_CSS%" (
        copy /Y "%CUSTOM_CSS%" "%CORE_CSS%"
        echo Successfully replaced core.css with custom-style.css.
    ) else (
        echo core.css not found at %CORE_CSS%.
    )
) else (
    echo custom-style.css not found at %CUSTOM_CSS%.
)

echo --- Local execution complete. Open your report and hard-refresh (Ctrl+F5)! ---
pause
