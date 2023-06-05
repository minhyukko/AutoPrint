from bardapi import Bard
import sys

def read_java_file(file_path):
    with open(file_path, 'r') as file:
        java_code = file.read()
    return java_code

def extract_substring(text):
    start_index = text.find('```java')  # Find the first occurrence of 'cat'
    end_index = text.rfind('```')   # Find the last occurrence of 'dog'

    if start_index == -1 or end_index == -1 or start_index >= end_index:
        return None  # Return None if either 'cat' or 'dog' is not found or if 'cat' appears after 'dog'

    return text[start_index+8:end_index]  # Extract the substring between 'cat' and 'dog' (including 'dog')
    

# Example usage:
# input_text = "I have a cat and a dog. My neighbor's cat is cute, but their dog is even cuter."
# substring = extract_substring(input_text)
# print(substring)

def write_to_file(file_path, content):
    try:
        with open(file_path, 'w') as file:
            file.write(content)
        print(f'Successfully wrote to file: {file_path}')
    except IOError:
        print(f'Error writing to file: {file_path}')

token = 'XAjWNkxvosNlEBbdm6_vaXAd7UweBZ7LAf_yz1AgCe70gySzMJJ-9ei5OWpgQSul_-9QIg.'
bard = Bard(token=token)
java_file_path = sys.argv[1] # Gets the file name from the command line argument.
print(sys.argv)

# i = 0
# lines = []
# for x in range(2, len(sys.argv) - 1):
#     print(sys.argv[x]) #Start Index
#     print(sys.argv[x+1]) #End Index

#     with open(java_file_path, "r") as file:
#         lines[i] = file.readlines()[int(sys.argv[x]):int(sys.argv[x+1])]  # Read lines X to X+1 (0-based indexing)

#     i = i + 1

#     # for line in lines:
#     #     print(line.strip())  # Print the line, removing leading/trailing whitespace


java_code = read_java_file(java_file_path) + "\n\nWhere should I insert print statements in this code for debugging? Give me the entire code with print statements included."
bardResponse = bard.get_answer(java_code)['content']
print(bardResponse)

# file_path = 'C:\\Users\\minhy\\Desktop\\output.txt'  # Specify the file path where you want to write the string
print(java_file_path)
write_to_file(java_file_path, extract_substring(bardResponse))


substring = extract_substring(bardResponse)
print(substring)
print(java_code)