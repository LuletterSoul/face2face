F:
cd F:\GitHup\face2face\protobuf\src\main\resources

set OUT=../java
set def_cli_java=(login chat)
set def_internal_java=(internal)
set def_server_java=(login friend file)


for %%A in %def_cli_java% do (
    echo generate cli protocol java code: %%A.proto
    protoc.exe --java_out=%OUT% ./cli_def/%%A.proto
)

for %%A in %def_internal_java% do (
    echo generate internal java code: %%A.proto
    protoc.exe --java_out=%OUT% ./internal_def/%%A.proto
)

for %%A in %def_server_java% do (
    echo generate server protocol java code: %%A.proto
    protoc.exe --java_out=%OUT% ./server_def/%%A.proto
)

pause