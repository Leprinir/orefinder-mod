Add-Type -AssemblyName System.Drawing
$base = "C:\Users\User\qclaw-oversea\workspace\minecraft-ore-finder-mod\src\main\resources\assets\orefinder\textures\item"
[System.IO.Directory]::CreateDirectory($base) | Out-Null

function New-CompassTexture($path, $r, $g, $b) {
    $bmp = New-Object System.Drawing.Bitmap(16, 16)
    for ($y = 0; $y -lt 16; $y++) {
        for ($x = 0; $x -lt 16; $x++) {
            $cx = $x - 7.5
            $cy = $y - 7.5
            $d = [Math]::Sqrt($cx * $cx + $cy * $cy)
            if ($d -lt 7) {
                if ($d -gt 5.5) {
                    $bmp.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(255, 40, 40, 40))
                } else {
                    $f = 1.0 - ($d / 7.0)
                    $pr = [int]($r * $f * 0.4)
                    $pg = [int]($g * $f * 0.4)
                    $pb = [int]($b * $f * 0.4)
                    $bmp.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(255, $pr, $pg, $pb))
                }
            } else {
                $bmp.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(0, 0, 0, 0))
            }
        }
    }
    $g2 = [System.Drawing.Graphics]::FromImage($bmp)
    $g2.Clear([System.Drawing.Color]::Transparent)
    for ($y = 0; $y -lt 16; $y++) {
        for ($x = 0; $x -lt 16; $x++) {
            $cx = $x - 7.5
            $cy = $y - 7.5
            $d = [Math]::Sqrt($cx * $cx + $cy * $cy)
            if ($d -lt 6) {
                $f = 1.0 - ($d / 6.5)
                $pr = [int]($r * $f)
                $pg = [int]($g * $f)
                $pb = [int]($b * $f)
                $bmp.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(255, $pr, $pg, $pb))
            }
        }
    }
    $g2.Dispose()
    $bmp.Save($path, [System.Drawing.Imaging.ImageFormat]::Png)
    $bmp.Dispose()
}

New-CompassTexture "$base\stone_ore_finder.png" 140 136 122
New-CompassTexture "$base\iron_ore_finder.png" 160 160 175
New-CompassTexture "$base\gold_ore_finder.png" 220 175 35
New-CompassTexture "$base\diamond_ore_finder.png" 40 180 230
New-CompassTexture "$base\emerald_master_finder.png" 30 200 80
New-CompassTexture "$base\ore_finder_upgrade.png" 150 60 210

Write-Host "OK"
Get-ChildItem $base | Select-Object Name, @{N = 'KB'; E = { [Math]::Round($_.Length / 1KB, 1) } }
