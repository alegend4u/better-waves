import os
from pathlib import Path
from eyed3 import id3
from BetterWaves import settings
from music import models


files = []
library_path = os.path.join(settings.MEDIA_ROOT, 'music')
entries = Path(library_path)
for entry in entries.iterdir():
    files.append(entry)

tag = id3.Tag()

for file in files:
    # Parse the file
    filepath = os.path.join(library_path, file.name)
    tag.parse(filepath)

    # Create Artist model and check if already exists
    artist = models.Artist(title=tag.artist)
    dup_artist = models.Artist.objects.get(title=tag.artist)
    if not dup_artist:
        print('Artist added:', tag.artist)
        artist.save()

    # Create Album model and check if already exists
    album = models.Album(title=tag.album, artist=artist)
    dup_album = models.Album.objects.get(title=tag.album)
    if not dup_album:
        print('Album added:', tag.album)
        album.save()

    # Create Song model and check if already exists
    s = models.Song(title=tag.title, artist=artist, album=album, genre=tag.genre, file=file.name)
    dup_song = models.Song.objects.get(file=file.name)
    if not dup_song:
        print('Song added:', file.name)
        s.save()
    print("="*10)
