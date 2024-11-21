#!bin/bash

echo "Starting monitoring in the background..."
echo "------ $(date) ------" >> usage_report.txt

echo "Chipset Model:" >> usage_report.txt
system_profiler SPDisplaysDataType | grep "Chipset Model" >> usage_report.txt
system_profiler SPHardwareDataType | grep "Total Number of Cores" >> usage_report.txt

echo "" >> usage_report.txt

echo "------- Detailed host Info ------" >> usage_report.txt
system_profiler SPHardwareDataType SPSoftwareDataType SPMemoryDataType SPStorageDataType >> usage_report.txt
echo "" >> usage_report.txt
echo "---------///---///---------" >> usage_report.txt

echo "" >> usage_report.txt

while true; do
    echo "------ $(date) ------" >> usage_report.txt
    echo "------ CPU & Memory Usage ------" >> usage_report.txt
    top -l 1 | head -n 10 >> usage_report.txt
    
    echo "" >> usage_report.txt
    echo "" >> usage_report.txt
    
    echo "------ Top CPU-Intensive Processes ------" >> usage_report.txt
    ps -Aro pid,comm,args,%cpu | head -n 10 >> usage_report.txt
    
    echo "" >> usage_report.txt
    echo "" >> usage_report.txt

    echo "------ Top RAM-Intensive Processes ------" >> usage_report.txt
    ps -Amo pid,comm,args,%mem | head -n 10 >> usage_report.txt
    
    echo "" >> usage_report.txt
    echo "" >> usage_report.txt

    echo "------ Memory Usage ------" >> usage_report.txt
    vm_stat | awk '
    /Pages free/ { free=$3 * 4096 / 1024 / 1024 }
    /Pages active/ { active=$3 * 4096 / 1024 / 1024 }
    /Pages inactive/ { inactive=$3 * 4096 / 1024 / 1024 }
    /Pages speculative/ { speculative=$3 * 4096 / 1024 / 1024 }
    END {
        printf "  Free: %.2f MB\n  Active: %.2f MB\n  Inactive: %.2f MB\n  Speculative: %.2f MB\n", free, active, inactive, speculative
    }' >> usage_report.txt
    
    echo "" >> usage_report.txt
    echo "" >> usage_report.txt
    
    echo "------ Disk Usage ------" >> usage_report.txt
    df -h >> usage_report.txt
    
    echo "" >> usage_report.txt
    echo "---------///---///---------" >> usage_report.txt
    echo "" >> usage_report.txt
    sleep 10
done & echo $! > monitor_pid.txt