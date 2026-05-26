from PIL import Image
from pathlib import Path

current_dir = Path(".")

for file in current_dir.glob("*.png"):
    output_path = current_dir / f"{file.stem}.webp"

    with Image.open(file) as img:
        img.save(output_path, "WEBP", quality=85)

    print(f"변환 완료: {file.name}")