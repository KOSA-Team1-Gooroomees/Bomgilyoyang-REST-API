from pathlib import Path

current_dir = Path(".")

for file in current_dir.iterdir():

    if file.suffix.lower() in [".png", ".jpeg"]:
        file.unlink()
        print(f"삭제 완료: {file.name}")