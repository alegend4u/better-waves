from django.core.files.storage import FileSystemStorage
from django.conf import settings
import os


class OverwriteStorage(FileSystemStorage):
    def get_available_name(self, name, max_length=None):
        """
        Returns a filename that's free on the target storage system, and
        available for new content to be written to.
        """
        # If the filename already exists, remove it as if it was a true file system
        if self.exists(name):
            return 'ALREADY_EXISTS'
        return name

    def save(self, name, content, max_length=None):
        new_name = self.get_available_name(name, max_length=max_length)
        if new_name == 'ALREADY_EXISTS':
            print(content)
            return content.name
        super().save(name, content, max_length=max_length)
