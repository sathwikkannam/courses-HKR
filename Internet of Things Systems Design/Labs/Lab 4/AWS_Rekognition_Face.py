import boto3
import json
import os


def detect_faces(target_file, region):
    client = boto3.client('rekognition', region_name=region)

    imageTarget = open(target_file, 'rb')

    response = client.detect_faces(Image={'Bytes': imageTarget.read()},
                                   Attributes=['ALL'])

    for faceDetail in response['FaceDetails']:
        print('The detected face is between ' + str(faceDetail['AgeRange']['Low'])
              + ' and ' + str(faceDetail['AgeRange']['High']) + ' years old')

        print('Here are the other attributes:')
        print(json.dumps(faceDetail, indent=4, sort_keys=True))

        # Access predictions for individual face details and print them
        print("Gender: " + str(faceDetail['Gender']))
        print("Smile: " + str(faceDetail['Smile']))
        print("Eyeglasses: " + str(faceDetail['Eyeglasses']))
        print("Emotions: " + str(faceDetail['Emotions'][0]))

    return len(response['FaceDetails'])


def compare_faces(bucket, sourceFile, targetFile, region):
    client = boto3.client('rekognition', region_name=region)

    imageTarget = open(targetFile, 'rb')

    response = client.compare_faces(SimilarityThreshold=99,
                                    SourceImage={'S3Object': {'Bucket': bucket, 'Name': sourceFile}},
                                    TargetImage={'Bytes': imageTarget.read()})

    for faceMatch in response['FaceMatches']:
        position = faceMatch['Face']['BoundingBox']
        similarity = str(faceMatch['Similarity'])
        print('The face at ' +
              str(position['Left']) + ' ' +
              str(position['Top']) +
              ' matches with ' + similarity + '% confidence')

    imageTarget.close()
    return len(response['FaceMatches'])


def compare_faces(bucket, sourceFile, targetFile, region):
    client = boto3.client('rekognition', region_name=region,
                          aws_access_key_id=os.getenv('ACCESS_KEY_ID'),
                          aws_secret_access_key=os.getenv('SECRET_ACCESS_KEY')
                          )

    imageTarget = open(targetFile, 'rb')
    response = client.compare_faces(SimilarityThreshold=99,
                                    SourceImage={'S3Object': {'Bucket': bucket, 'Name': sourceFile}},
                                    TargetImage={'Bytes': imageTarget.read()})

    for faceMatch in response['FaceMatches']:
        position = faceMatch['Face']['BoundingBox']
        similarity = str(faceMatch['Similarity'])
        print('The face at ' +
              str(position['Left']) + ' ' +
              str(position['Top']) +
              ' matches with ' + similarity + '% confidence')

    imageTarget.close()
    return len(response['FaceMatches'])


def detect_face():
    bucket = 'dt373bucket'
    source_folder = "lab4-michel-sathwik-pictures/"
    source_files = ["michel", "michel1", "michel2", "michel3", "michel4", "michel5", "sathwik", "sathwik1", "sathwik2",
                    "sathwik3", "sathwik4", "sathwik5"]
    target_file = '/home/satva/Desktop/rpiws/static/boogeraids.jpg'
    region = "us-east-1"

    results = []

    faceMatchFound = False

    for source_file in source_files:
        face_matches = compare_faces(bucket, source_folder + source_file + ".jpg", target_file, region)
        if str(face_matches) == "1":
            faceMatchFound = True
            break

    if faceMatchFound:
        print("Face match found.")
    else:
        print("No face match found.")

    return faceMatchFound
