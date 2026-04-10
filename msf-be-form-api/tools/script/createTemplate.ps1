param(
    [Parameter(Mandatory = $true)]
    [ValidateSet("hexagonal", "legacy", "module")]
    [string]$Type,

    [Parameter(Mandatory = $true)]
    [string]$BaseDir,

    [string]$ModuleName
)

$task = switch ($Type) {
    "hexagonal" { "createHexagonalPackages" }
    "legacy" { "createLegacyPackages" }
    "module" { "createGradleModule" }
}

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$ProjectRoot = Resolve-Path (Join-Path $ScriptDir "../..")

Set-Location $ProjectRoot
if ($Type -eq "module") {
    if ([string]::IsNullOrWhiteSpace($ModuleName)) {
        throw "ModuleName is required when Type is 'module'."
    }

    & .\gradlew.bat $task "-PbaseDir=$BaseDir" "-PmoduleName=$ModuleName"
} else {
    & .\gradlew.bat $task "-PbaseDir=$BaseDir"
}
