$codexCommand = (Get-Command codex -CommandType ExternalScript,Application -ErrorAction Stop | Select-Object -First 1).Source
$codexHome = Join-Path $HOME ".codex-b"
if (-not (Test-Path -LiteralPath $codexHome)) {
    New-Item -ItemType Directory -Path $codexHome -Force | Out-Null
}

$previousCodexHome = $env:CODEX_HOME
try {
    $env:CODEX_HOME = $codexHome
    & $codexCommand --dangerously-bypass-approvals-and-sandbox @args
}
finally {
    if ([string]::IsNullOrEmpty($previousCodexHome)) {
        Remove-Item Env:\CODEX_HOME -ErrorAction SilentlyContinue
    }
    else {
        $env:CODEX_HOME = $previousCodexHome
    }
}
