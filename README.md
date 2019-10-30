# Better Waves

A music player application that throws great recommendations for you.
Visit https://youtu.be/ltS9cclgL20 for demo.

## Manual
The repository contains both platforms: Web and Android.
Let's get the server running first!
1. Install and activate a virtual environment (pipenv or virtualenv) using requirements.txt.
2. Navigate to /Platform/Web/BetterWaves.
3. Run the following command:`python manage.py runserver <HOST IP Address>:<PORT>`

Now that our server is running, let's configure our android project.
File to change: `Platform/Android/Better_Waves/app/src/main/res/values/strings.xml`

In `strings.xml` file, `<string name="base_url"><HOST IP Address>:<PORT></string>` is the value we need to change. Use previously used IP address and PORT number so that the android application uses it as server to make requests.
> Example: `<string name="base_url">http://192.168.43.165:8000/</string>`

### That's it! Build and compile the android project and run it on AVD or Phone.
