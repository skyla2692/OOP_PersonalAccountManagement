# OOP_PersonalAccountManagement
Connected Java file and Excel file by using apache POI. This leads getting data from user by GUI, and save in Excel file, which will be called from GUI again. There is a difference in functions between older versions and newest version of POI and Excel. The written code is structured for POI 4.1.1 and Excel format of .xlsx.

## Simple Diagram
![project_proposal](https://user-images.githubusercontent.com/42270720/87799018-abff5900-c887-11ea-8ce4-9f7d48af11f2.png)

## Features
- Method of Income and Expense Tab
  - Simple explanation of the method of income and expense will be written by user.
- Amount Tab
  - The amount of money spent or came in to deposit will be written in the command line by user.
- IN / OUT Button
  - If IN button is clicked, the amount and method of money entered by user will be automatically written in connected excel file as Income.
  - If OUT button is clicked, the amount and method of money entered by user will be automatically written in connected excel file as Expense.
- Total Amount Tab
  - The total amount of the deposit will be shown in the tab.
  - As the user enter new data in, the total amount will be up to the date.
- Check Log Button
  - The button will show the excel file in new page. 

## Environment
- IDE : IntelliJ IDEA, Microsoft Excel
- Language : Java

## Pre-Installation
- Apache poi 4.1.1 : https://poi.apache.org/download.html#POI-4.1.1
- Apache common collections 4.4 : https://mvnrepository.com/artifact/org.apache.commons/commons-collections4/4.4
- Apache commons compress 1.19 : https://commons.apache.org/proper/commons-compress/download_compress.cgi

## Install
- Git clone or Download ZIP

## How to Use
- Excel File
  - Make your own file with tabs that you defined in java code
- Java Code File
  - Edit the link of excel file in the code to your own excel file
