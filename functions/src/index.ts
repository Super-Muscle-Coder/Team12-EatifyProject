import * as functions from "firebase-functions/v1";
import * as admin from "firebase-admin";
admin.initializeApp();
export const createUserDocument = functions.auth.user().onCreate(
  (userRecord: functions.auth.UserRecord) => {
    const uid = userRecord.uid;
    return admin.firestore().collection("users").doc(uid).set({
      userId: uid,
      email: userRecord.email,
      name: userRecord.displayName || "",
      phoneNumber: userRecord.phoneNumber || "",
      address: "",
      avatarUrl: userRecord.photoURL || "",
    });
  }
);
