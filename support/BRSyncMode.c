//
//  BRSyncMode.c
//  BRCore
//
//  Created by Ed Gamble on 3/18/19.
//  Copyright © 2019 breadwallet. All rights reserved.
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files (the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//  THE SOFTWARE.
//

#include "BRSyncMode.h"

extern BRDisconnectReason
BRDisconnectReasonRequested() {
    return (BRDisconnectReason) {
        DISCONNECT_REASON_REQUESTED
    };
}

extern const BRDisconnectReason
BRDisconnectReasonUnknown() {
    return (BRDisconnectReason) {
        DISCONNECT_REASON_UNKNOWN
    };
}

extern const BRDisconnectReason
BRDisconnectReasonPosix(int errnum) {
    return (BRDisconnectReason) {
        DISCONNECT_REASON_POSIX,
        { .posix = errnum }
    };
}

extern const char *
BRSyncModeString (BRSyncMode m) {
    switch (m) {
        case SYNC_MODE_BRD_ONLY:
        return "SYNC_MODE_BRD_ONLY";
        case SYNC_MODE_BRD_WITH_P2P_SEND:
        return "SYNC_MODE_BRD_WITH_P2P_SEND";
        case SYNC_MODE_P2P_WITH_BRD_SYNC:
        return "SYNC_MODE_P2P_WITH_BRD_SYNC";
        case SYNC_MODE_P2P_ONLY:
        return "SYNC_MODE_P2P_ONLY";
    }
}
