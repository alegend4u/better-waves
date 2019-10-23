from music import models
import pandas as pd
from music import Recommenders
from sklearn.model_selection import train_test_split


class RecommendationEngine:
    def __init__(self):
        self.POPULARITY = 'Popularity'
        self.ITEM_SIMILARITY = 'Item_Similarity'

        self.recommend_mode = self.POPULARITY
        self.pm = self.POPULARITY

    def retrain(self):
        print("=====Training=====")
        user_songs = models.UserSong.objects.all()
        user_songs_dict = {
            'user_id': [],
            'song_id': [],
            'listen_count': []
        }
        for user_song in user_songs:
            user_id = user_song.user_of_song_id
            song_id = user_song.song_id
            listen_count = user_song.listen_count
            user_songs_dict['user_id'].append(user_id)
            user_songs_dict['song_id'].append(song_id)
            user_songs_dict['listen_count'].append(listen_count)
        print("=====Data Framed!=====")
        data = pd.DataFrame(user_songs_dict)
        train_data, test_data = train_test_split(data, test_size=0.20, random_state=0)
        self.pm.create(train_data, 'user_id', 'song_id')

    def select_recommender(self):
        if self.recommend_mode == self.ITEM_SIMILARITY:
            return Recommenders.item_similarity_recommender_py()
        else:
            return Recommenders.popularity_recommender_py()

    def recommend(self, user_id):

        # Decide the recommender and train the model
        user = models.User.objects.get(id=user_id)
        rated_songs = user.rated_songs.get_queryset()
        print("User's rated songs count: ", len(rated_songs))
        if len(rated_songs) > 0:
            self.recommend_mode = self.ITEM_SIMILARITY
        self.pm = self.select_recommender()
        self.retrain()

        # Get the recommendations
        print("Recommendation Mode:", self.recommend_mode)
        result = dict(self.pm.recommend(user_id))
        if self.recommend_mode == self.POPULARITY:
            song_ids = list(result['song_id'])
        else:
            song_ids = list(result['song'])

        # Get the song objects
        result = []
        for i in range(len(song_ids)):
            song = models.Song.objects.get(id=song_ids[i])
            result.append(song)
        return result
