# This file is purely based on the mp3 files preprocess to make file attributes relevant
import eyed3
import os
import random
from eyed3.id3 import Genre
from pathlib import Path
from django.conf import settings

GENRE_LIST = [
    'Pop',
    'Rock',
    'Electronic',
    'Soundtrack',
    'Hip-Hop',
    'Club',
    'Classical',
    'R&B',
    'Blues',
    'Metal'
]
GENRES = []
for i in GENRE_LIST:
    GENRES.append((Genre(i).id, Genre(i).name))


def generate_random_genres():
    media = settings.MEDIA_ROOT
    entries = Path(os.path.join(media, 'music'))
    files = []
    for entry in entries.iterdir():
        files.append(entry)

    for file in files:
        filepath = os.path.join(media, 'music',file.name)
        audiofile = eyed3.load(filepath)

        #Get random genre from list
        selected_genre = random.choice(GENRES)
        # print(selected_genre)
        audiofile.tag.genre = selected_genre[1]
        audiofile.tag.save()
