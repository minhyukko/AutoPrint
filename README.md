# AutoPrint: Automatic print statement insertion tool
AutoPrint is a tool developed to assist the insertion or removal of print statements for debugging purposes. AutoPrint supports Java programming language. Download link: https://people.cs.vt.edu/minhyukko/autoprint/

## Usage

1. Open `AutoPrint.jar`
2. Specify the directory location of where their working files are located in. All files that end with `.java` extension in the directory will have the print statements inserted or removed.
3. If removing a print statement, users can determine whether to **remove print statements** generated by AutoPrint, or **remove all print statements** regardless of whether it was written by AutoPrint, user, or third-party applications.
4. Specify the line number where to start inserting the print statements and end inserting print statements. This condition will be applied to all Java files located in the directory.
5. Specify the variables to print, with each variable separated by a space. If no variables are specified, all variables will be printed. If at least one variables are specified, only the variables specified will be printed.


## Source Code

Source code is located in `AutoPrint/hello/com.remainsoftware.e3.hello/src/com/remainsoftware/e3/hello/`.
